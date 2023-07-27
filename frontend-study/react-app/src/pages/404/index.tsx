import { memo } from 'react';
import { Button, Result } from 'antd';
import { RollbackOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const NotFoundPage = memo(() => {
  const navigate = useNavigate();

  const goBackHome = () => {
    navigate('/');
  };

  return (
    <Result
      status="404"
      title="404"
      subTitle="对不起，您访问的页面不存在。"
      extra={
        <Button type="primary" icon={<RollbackOutlined />} onClick={goBackHome}>
          返回首页
        </Button>
      }
    />
  );
});

export default NotFoundPage;
