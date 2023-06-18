import axios, {AxiosInstance, AxiosError, AxiosResponse, AxiosRequestConfig} from 'axios';
//创建一个axios对象
const service:AxiosInstance = axios.create({
    timeout: 5000,
    baseURL: '/api'
});

service.interceptors.request.use(
    (config: AxiosRequestConfig) => {
      // 在这里，可以配置请求头、token等信息
        return config;
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    (response: AxiosResponse) => {
        if (response.status === 200) {
            return response;
        } else {
            Promise.reject();
        }
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
