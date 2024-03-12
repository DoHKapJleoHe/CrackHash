rs.initiate({
    _id: "rs0",
    members: [
        { _id: 0, host: "mongodb-primary:27017" },
        { _id: 1, host: "mongodb-secondary1:27017" },
        { _id: 2, host: "mongodb-secondary2:27017" }
    ]
});