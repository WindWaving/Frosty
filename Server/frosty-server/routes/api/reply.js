const { Comment,User,Reply } = require('../../models');
var router = require('koa-router')();
router.prefix('/reply')
const upload=require('../../multer-config')

//删除回复
router.delete('/:rid',async(ctx)=>{
    let {rid}=ctx.params;
    try{
        await Reply.destroy({
            where:{rid:rid}
        })
        ctx.body={
            err:0,
            info:"删除成功"
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"删除错误，"+err
        }
    }
})

//添加回复
router.post('/',upload.fields([{
    name:"images",
    maxCount:9
}]),async(ctx)=>{
    let {uid,content,parentId}=ctx.request.body
    let {images} = ctx.request.files;
    var imagePaths="";
    if(images&&images.length){
        images.map(file=>{
            let path=file.path.replace(/\\/g,'/');
            path=path.substring(7);//删除public路径名
            imagePaths+=`${ctx.state.serverUrl}${path};`
        })
    }
    try{
        await Reply.create({
            parentId:parentId,
            uid:uid,
            content:content,
            images:imagePaths
        })
        ctx.body={
            err:0,
            info:"发布成功"
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"发布错误，"+err
        }
    }
})

module.exports=router