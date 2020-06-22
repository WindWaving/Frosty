const { Topic,Comment } = require('../../models')
var router = require('koa-router')();
router.prefix('/topic')

router.get('/',async(ctx)=>{
    let {offset,limit}=ctx.request.query;
    try{
        let res=await Topic.findAll({
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
        let res=await Topic.findAll({
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

router.post('/',async(ctx)=>{
    let {content,authorId}=ctx.request.body;
    try{
        let res=await Topic.create({
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

router.delete('/:tid',async(ctx)=>{
    let {tid}=ctx.params;
    try{
        await Topic.destroy({
            where:{tid:tid}
        })
        await Comment.destroy({
            where:{
                parentId:tid,
                type:"topic"
            }
        })
        await Favorites.destroy({
            where:{
                itemId:tid,
                type:"topic"
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

router.patch('/:tid',async(ctx)=>{
    let {tid}=ctx.params;
    let {musicUrl,words}=ctx.request.body;
    try{
        await Topic.update({
            content:content
        },{
            where:{tid:tid}
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