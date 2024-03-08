
var pipeline = 
[
    {
        $lookup: {
            "from": "comment",
            "localField": "_id",
            "foreignField": "productId",
            "as": "comments"
        }
    },
    {
        $project: {
            "_id": 1,
            "name": 1,
            "price": 1,
            "comments": "$comments.text",
            "oneStar": 1,
            "twoStars": 1,
            "threeStars": 1,
            "fourStars": 1,
            "fiveStars": 1
        }
    },
    {
        $limit: 10
    }
];

db.product.aggregate(pipeline);
