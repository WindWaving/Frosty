const { Comment,User,Reply } = require('../../models');
var router = require('koa-router')();
router.prefix('/reply')

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
router.post('/',async(ctx)=>{
    let {uid,content,parentId}=ctx.request.body
    try{
        await Reply.create({
            parentId:parentId,
            uid:uid,
            content:content
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