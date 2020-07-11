const { Comment,User,Reply } = require('../../models');
var router = require('koa-router')();
const upload=require('../../multer-config')
router.prefix('/comments')

//获取某条评论的回复
router.get('/:cid/replies',async(ctx)=>{
    let {offset,limit}=ctx.request.query;
    let {cid}=ctx.params
    try{
        let res=await Reply.findAll({
            where:{parentId:cid},
            offset:+offset,
            limit:+limit,
            include:[{
                model:User,
                attributes:['nickname']
            }]
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

//获取某条发布的评论
router.get('/:type/:parentId',async(ctx)=>{
    let {type,parentId}=ctx.params;
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Comment.findAll({
            where:{
                type:type,
                parentId:parentId
            },
            offset:+offset,
            limit:+limit,
            include:[{
                model:User,
                attributes:['nickname']               
            }]
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

//删除评论及回复
router.delete('/:cid',async(ctx)=>{
    let {cid}=ctx.params;
    try{
        await Comment.destroy({
            where:{cid:cid}
        })
        // await Reply.destroy({
        //     where:{parentId:cid}
        // })
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

//发布评论
router.post('/',upload.fields([{
    name:"images",
    maxCount:9
}]),async(ctx)=>{
    let {type,uid,content,parentId}=ctx.request.body
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
        await Comment.create({
            parentId:parentId,
            type:type,
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