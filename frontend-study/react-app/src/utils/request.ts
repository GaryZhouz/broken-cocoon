import axios, { AxiosResponse } from 'axios';
import { message } from 'antd';

const { VITE_SEVER_HOST, VITE_SEVER_TIMEOUT } = import.meta.env;

const service = axios.create({
  baseURL: VITE_SEVER_HOST,
  timeout: VITE_SEVER_TIMEOUT,
});

service.interceptors.response.use(
  (response: AxiosResponse<any>) => {
    const res = response.data;
    if (res.code !== 200) {
      message.error(res.message);

      return Promise.reject('request error');
    } else {
      return response.data;
    }
  },
  (error) => {
    console.log('err' + error);
    message.error(error.response.data.message);
    return Promise.reject(error);
  },
);

export default service;
