const { Picture,Comment } = require('../../models')
var router = require('koa-router')();
router.prefix('/picture')
const upload=require('../../multer-config')

router.get('/',async(ctx)=>{
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Picture.findAll({
            offset:+offset,
            limit:+limit
        });
        ctx.body={
            err:0,
            info:{
                data:res
            }
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"获取失败，"+err
        }
    }
})

router.get('/:authorId',async(ctx)=>{
    let {authorId}=ctx.params;
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Picture.findAll({
            offset:+offset,
            limit:+limit,
            where:{authorId:authorId}
        })
        ctx.body={
            err:0,
            info:{
                data:res
            }
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"获取错误，"+err
        }
    }
})

router.post('/',upload.fields([{
    name: 'images',
    maxCount: 1
}]),async(ctx)=>{
    let {content,authorId}=ctx.request.body;
    let {images} = ctx.request.files;
    var imgUrl="";
    if(images&&images.length==1){
        console.log(images,images.length);
        let path=images[0].path.replace(/\\/g,'/');
        path=path.substring(7);//删除public路径名
        imgUrl=`${ctx.state.serverUrl}${path}`
        try{
            await Picture.create({
                content:content,
                imgUrl:imgUrl,
                authorId:authorId
            })
            ctx.body={
                err:0,
                info:"添加成功"
            }
        }catch(err){
            ctx.body={
                err:-2,
                info:"添加错误，"+err
            }
        }
    }else{
        ctx.body={
            err:-1,
            info:"图片数量不合法"
        }
    }
})

router.delete('/:pid',async(ctx)=>{
    let {pid}=ctx.params;
    try{
        await Picture.destroy({
            where:{pid:pid}
        })
        await Comment.destroy({
            where:{
                parentId:pid,
                type:"picture"
            }
        })
        await Favorites.destroy({
            where:{
                itemId:pid,
                type:"picture"
            }
        })
        ctx.body={
            err:0,
            info:"删除成功"
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"删除失败，"+err
        }
    }
})

router.patch('/:pid',async(ctx)=>{
    let {pid}=ctx.params;
    let {imgUrl,content}=ctx.request.body;
    try{
        await Picture.update({
            content:content,
            imgUrl:imgUrl
        },{
            where:{pid:pid}
        })
        ctx.body={
            err:0,
            info:"修改成功"
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"修改失败，"+err
        }
    }
})

module.exports=router