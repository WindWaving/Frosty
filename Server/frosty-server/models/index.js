const {Sequelize,sequelize}=require('./db')
const Music=require('./music')
const Picture=require('./picture')
const Topic=require('./topic')
const User=require('./user')
const Yiyan=require('./yiyan')
const Favorites=require('./favorites')
const Reply=require('./reply')
const Comment=require('./comment')

User.hasMany(Yiyan,{foreignKey:'authorId',onDelete:'cascade'})
Yiyan.belongsTo(User,{foreignKey:'authorId'})

User.hasMany(Music,{foreignKey:'authorId',onDelete:'cascade'})
Music.belongsTo(User,{foreignKey:'authorId'})

// Music.hasMany(Comment,{foreignKey:'parentId'})
// Comment.belongsTo(Music,{foreignKey:'parentId'})

User.hasMany(Picture,{foreignKey:'authorId',onDelete:'cascade'})
Picture.belongsTo(User,{foreignKey:'authorId'})

// Picture.hasMany(Comment,{foreignKey:'parentId'})
// Comment.belongsTo(Picture,{foreignKey:'parentId'})

User.hasMany(Topic,{foreignKey:'authorId',onDelete:'cascade'})
Topic.belongsTo(User,{foreignKey:'authorId'})

// Topic.hasMany(Comment,{foreignKey:'parentId'})
// Comment.belongsTo(Topic,{foreignKey:'parentId'})

Comment.hasMany(Reply,{foreignKey:'parentId',onDelete:'cascade'})
Reply.belongsTo(Comment,{foreignKey:'parentId'})

User.hasMany(Comment,{foreignKey:'uid',onDelete:'cascade'})
Comment.belongsTo(User,{foreignKey:'uid'})

User.hasMany(Reply,{foreignKey:'uid',onDelete:'cascade'})
Reply.belongsTo(User,{foreignKey:'uid'})

User.hasMany(Favorites,{foreignKey:'uid',onDelete:'cascade'})
Favorites.belongsTo(User,{foreignKey:'uid'})

// Yiyan.hasMany(Favorites,{foreignKey:'itemId'})
// Favorites.belongsTo(Yiyan,{foreignKey:'itemId'})

// Music.hasMany(Favorites,{foreignKey:'itemId'})
// Favorites.belongsTo(Music,{foreignKey:'itemId'})

// Picture.hasMany(Favorites,{foreignKey:'itemId'})
// Favorites.belongsTo(Picture,{foreignKey:'itemId'})

// Topic.hasMany(Favorites,{foreignKey:'itemId'})
// Favorites.belongsTo(Topic,{foreignKey:'itemId'})

User.sync();
Yiyan.sync();
Music.sync();
Picture.sync()
Topic.sync();
Favorites.sync();
Comment.sync();
Reply.sync();

module.exports={
    Sequelize,
    sequelize,
    Music,
    Picture,
    Topic,
    User,
    Yiyan,
    Comment,
    Reply,
    Favorites
}