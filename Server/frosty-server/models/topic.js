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
    },
    images:{
        type:Sequelize.STRING,
        allowNull:true
    }
    //authorId:Sequelize.INTEGER
})

module.exports=Topic