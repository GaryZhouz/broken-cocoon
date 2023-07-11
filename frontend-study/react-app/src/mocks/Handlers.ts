import { MockedRequest, ResponseComposition, rest, RestContext } from 'msw';
import cities from './cities.json';
import City from '../models/City.ts';
import { Response } from '../models/Response.ts';
import RegisterRequest from '../models/RegisterRequest.ts';

export const handlers = [
  rest.get('/cities', (_: MockedRequest, res: ResponseComposition<Response<City[]>>, ctx: RestContext) => {
    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        data: cities,
        message: 'success',
      }),
    );
  }),
  rest.post('/register', async (req: MockedRequest, res: ResponseComposition<Response<any>>, ctx: RestContext) => {
    const registerBody = await req.json<RegisterRequest>();
    const { username, password, area } = registerBody;
    if (!registerBody || !username || !password || !area) {
      return res(
        ctx.status(400),
        ctx.json({
          code: 400,
          data: null,
          message: 'Request params lose!',
        }),
      );
    }
    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        data: {
          username,
          password,
          area,
        },
        message: 'success',
      }),
    );
  }),
];
