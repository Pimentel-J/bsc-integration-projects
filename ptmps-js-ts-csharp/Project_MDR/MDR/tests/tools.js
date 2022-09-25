/**
 * Mocks generalizados para Request, Response e Next
 */

const mockRequest = () => {
    const req = {};
    req.body = jest.fn().mockReturnValue(req);
    return req;
};

const mockResponse = () => {
    const res = {};
    res.status = jest.fn().mockReturnValue(res);
    res.json = jest.fn().mockReturnValue(res);
    return res;
};

const mockNext = () => {
    return jest.fn();
};

module.exports = { mockResponse, mockRequest, mockNext };