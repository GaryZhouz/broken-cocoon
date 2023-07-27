import '../styles/Register.scss';
import React, { useEffect, useState } from 'react';
import { Button, Card, Cascader, Form, Input, message, notification } from 'antd';
import api from '../api/Api.ts';
import type { DefaultOptionType } from 'antd/es/cascader';
import { Response } from '../models/Response.ts';
import City from '../models/City.ts';

interface RegisterFormData {
  username: string;
  password: string;
  confirmPassword: string;
  area: string[];
}

const Register: React.FC = () => {
  const [registerFormData] = Form.useForm<RegisterFormData>();
  const [loadingAreaData, setLoadingAreaData] = useState<boolean>(true);
  const [cities, setCities] = useState<City[]>([]);
  useEffect(() => {
    api
      .getCities()
      .then((resp: Response<City[]>) => {
        setCities((prev: City[]) => {
          prev.splice(0, prev.length);
          prev.push(...resp.data);
          return prev;
        });
        setLoadingAreaData((_: boolean) => false);
      })
      .finally(() => setLoadingAreaData((_: boolean) => false));
  }, []);

  const filterArea = (inputValue: string, path: DefaultOptionType[]) =>
    path.some(
      (option: DefaultOptionType) => (option.label as string)?.toLowerCase()?.indexOf(inputValue.toLowerCase()) > -1,
    );

  const toRegister = async (formData: RegisterFormData) => {
    const { username, password, area } = formData;
    const resp = await api.register({
      username,
      password,
      area: area.join('/'),
    });
    console.log(resp);
    notification.success({
      message: `注册成功`,
      description: (
        <div>
          <p>username: {resp.data.username}</p>
          <p>area: {resp.data.area}</p>
        </div>
      ),
      placement: 'topRight',
    });
    registerFormData.resetFields();
  };

  return (
    <div className="register-container">
      <Card className="card-container" title="开始注册" loading={loadingAreaData}>
        <Form
          form={registerFormData}
          name="basic"
          style={{ width: '100%' }}
          layout="vertical"
          initialValues={{ remember: true }}
          autoComplete="off"
          onFinish={toRegister}
          onFinishFailed={() => message.warning('请填写必要参数')}
        >
          <Form.Item
            label="Username"
            name="username"
            rules={[{ required: true, message: 'Please input your username!' }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Password"
            name="password"
            rules={[{ required: true, message: 'Please input your password!' }]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            label="Confirm password"
            name="confirmPassword"
            rules={[
              {
                required: true,
                message: 'Please confirm your password!',
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue('password') === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error('The two passwords are inconsistent'));
                },
              }),
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item label="Area" name="area" rules={[{ required: true, message: 'Please select your area!' }]}>
            <Cascader options={cities} showSearch={{ filter: filterArea }} placeholder="Please select your area." />
          </Form.Item>

          <Form.Item>
            <Button style={{ width: '100%' }} type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default Register;
