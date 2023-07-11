import request from '../utils/request.ts';
import { Response } from '../models/Response.ts';
import City from '../models/City.ts';
import RegisterRequest from '../models/RegisterRequest.ts';

export default {
  getCities(): Promise<Response<City[]>> {
    return request({
      url: '/cities',
      method: 'get',
    });
  },
  register(body: RegisterRequest): Promise<Response<{ username: string; area: string }>> {
    return request({
      url: '/register',
      method: 'post',
      data: body,
    });
  },
};
