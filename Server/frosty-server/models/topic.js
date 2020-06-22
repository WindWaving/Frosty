const {sequelize,Sequelize}=require("./db")

const Topic=sequelize.define('topic',{
    tid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    content:Sequelize.TEXT,
    likes:{
        type:Sequelize.INTEGER,
        defaultValue:0
    }
    //authorId:Sequelize.INTEGER
})

module.exports=Topic