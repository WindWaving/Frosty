const { Music,Comment,Favorites,Sequelize,User } = require('../../models')
var router = require('koa-router')();
const upload=require('../../multer-config')
router.prefix('/music')
const Op=Sequelize.Op

router.get('/',async(ctx)=>{
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Music.findAndCountAll({
            offset:+offset,
            limit:+limit,
            include:[{
                model:User,
                attributes:['nickname']
            }]
        });
        ctx.body={
            err:0,
            info:{
                data:res.rows,
                total:res.count
            }
        }
    }catch(err){
        ctx.body={
            err:-2,
            info:"获取失败，"+err
        }
    }
})
//批量获取
// router.get('/batches',async(ctx)=>{
//     let {mids}=ctx.request.body;
//     let {offset,limit}=ctx.request.query;
//     try{
//         let res=await Music.findAll({
//             where:{
//                 mid:{[Op.in]:mids}
//             },
//             offset:+offset,
//             limit:+limit
//         })
//         ctx.body={
//             err:0,
//             info:{
//                 data:res
//             }
//         }
//     }catch(err){
//         ctx.body={
//             err:-2,
//             info:"获取失败，"+err
//         }
//     }
// })
router.get('/:mid',async(ctx)=>{
    let {mid}=ctx.params;
    try{
        let res=await Music.findOne({
            where:{mid:mid}
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
            info:"获取失败，"+err
        }
    }
})



router.get('/:authorId',async(ctx)=>{
    let {authorId}=ctx.params;
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Music.findAndCountAll({
            offset:+offset,
            limit:+limit,
            where:{authorId:authorId}
        })
        ctx.body={
            err:0,
            info:{
                data:res.rows,
                total:res.count
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
    name:"images",
    maxCount:9
}]),async(ctx)=>{
    let {name,musicUrl,words,authorId}=ctx.request.body;
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
        let res=await Music.create({
            musicUrl:musicUrl,
            words:words,
            name:name,
            authorId:authorId,
            images:imagePaths
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
})

router.delete('/:mid',async(ctx)=>{
    let {mid}=ctx.params;
    try{
        await Music.destroy({
            where:{mid:mid}
        })
        await Comment.destroy({
            where:{
                parentId:mid,
                type:"music"
            }
        })
        await Favorites.destroy({
            where:{
                itemId:mid,
                type:"music"
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

router.patch('/:mid',async(ctx)=>{
    let {mid}=ctx.params;
    let {musicUrl,words,name}=ctx.request.body;
    try{
        await Music.update({
            musicUrl:musicUrl,
            words:words,
            name:name
        },{
            where:{mid:mid}
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