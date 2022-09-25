# Simple Joi Example
```javascript
/**
 * Response to http POST request to create a new course
 */
app.post('/api/courses', (req, res, next) => {
    const course = {
        id: courses.length + 1, // The DB generates an ID. Here there's no DB.
        name: req.body.name
    };

    // Validate request body against schema (object destructuring - result.error)
    const { error } = validateCourse(req.body);
    if (error) { 
        console.log(error);
        return res.status(400).json({error: error.message});
    }        
    courses.push(course);
    console.log(result.value);
    res.json(course);
    next();
});

function validateCourse(course) {
    // Joi validation example - create schema object
    const schema = Joi.object({
        name: Joi.string().min(3).required()
    });
    return schema.validate(course);
}

```
Source: [How to build a REST API with Node js & Express](https://www.youtube.com/watch?v=pKd0Rpw7O48)