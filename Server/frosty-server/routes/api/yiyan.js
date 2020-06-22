const { Yiyan,Comment } = require('../../models')
var router = require('koa-router')();
router.prefix('/yiyan')

//获取所有
router.get('/',async(ctx)=>{
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Yiyan.findAll({
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
            info:"获取错误，"+err
        }
    }

})
//某个用户的发布
router.get('/:authorId',async(ctx)=>{
    let {authorId}=ctx.params;
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Yiyan.findAll({
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
//添加
router.post('/',async(ctx)=>{
    let {content,authorId}=ctx.request.body;
    try{
        let res=await Yiyan.create({
            content:content,
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
})
//删除
router.delete('/:yid',async(ctx)=>{
    let {yid}=ctx.params;
    try{
        await Yiyan.destroy({
            where:{yid:yid}
        })
        await Comment.destroy({
            where:{
                parentId:yid,
                type:"yiyan"
            }
        })
        await Favorites.destroy({
            where:{
                itemId:yid,
                type:"yiyan"
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
//修改
router.patch('/:yid',async(ctx)=>{
    let {yid}=ctx.params;
    let {content}=ctx.request.body;
    try{
        await Yiyan.update({
            content:content
        },{
            where:{yid:yid}
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