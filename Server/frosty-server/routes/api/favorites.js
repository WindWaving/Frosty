const { Favorites,sequelize } = require('../../models');
var router = require('koa-router')();
router.prefix('/favor')

//某个用户某种类型的收藏夹
router.get('/:uid/:type',async(ctx)=>{
    let {uid,type}=ctx.params;
    let {offset,limit}=ctx.request.query;
    try{
        var collection="";
        var id="";
        switch(type){
            case "music":{collection="music";id="mid";break;}
            case "yiyan":{collection="yiyans";id="yid";break;}
            case "picture":{collection="pictures";id="pid";break;}
            case "topic":{collection="topics";id="tid";break;}
        }
        let sql=`select *from favorites,${collection} where uid=${uid} and itemId=${id} limit ${+offset},${+limit}`
        let res=await sequelize.query(sql,{ type: sequelize.QueryTypes.SELECT });
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

//加入收藏夹
router.post('/',async(ctx)=>{
    let {uid,type,itemId}=ctx.request.body;
    try{
        let res=await Favorites.findOne({
            where:{
                type:type,
                itemId:itemId
            }
        })
        if(res){
            ctx.body={
                err:-1,
                info:"该项已经存在收藏夹"
            }
        }else{
            await Favorites.create({
                uid:uid,
                type:type,
                itemId:itemId
            })
            ctx.body={
                err:0,
                info:"添加成功"
            }
        }
        
    }catch(err){
        ctx.body={
            err:-2,
            info:"添加失败，"+err
        }
    }
})

router.delete('/:fid',async(ctx)=>{
    let {fid}=ctx.params;
    try{
        await Favorites.destroy({
            where:{fid:fid}
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

//清空用户某个类型的收藏夹
router.delete('/:uid/:type',async(ctx)=>{
    let {uid,type}=ctx.params;
    try{
        await Favorites.destroy({
            where:{
                uid:uid,
                type:type
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
module.exports=router