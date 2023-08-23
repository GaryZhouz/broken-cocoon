import '../../styles/shop/layout.scss';
import React, { useEffect, useState } from 'react';
import { Link, Outlet, useLocation, useNavigate } from 'react-router-dom';
import { RocketOutlined, ShoppingCartOutlined } from '@ant-design/icons';
import { Spin } from 'antd';
import { showGlobalLoading } from '../../store/store.ts';
import { useRecoilValue } from 'recoil';

const Layout: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [selectedButtonIndex, setSelectedButtonIndex] = useState<number>(-1);
  useEffect(() => {
    if (location.pathname === '/') {
      navigate('/shop');
    } else if (location.pathname === '/shop') {
      setSelectedButtonIndex(0);
    } else if (location.pathname === '/cart') {
      setSelectedButtonIndex(1);
    }
  }, [location.pathname, navigate]);

  const getFooterButtonClassname = (index: number): string => {
    if (index === selectedButtonIndex) {
      return 'footer-btn-group active-btn';
    }
    return 'footer-btn-group';
  };

  const globalLoading = useRecoilValue(showGlobalLoading);

  return (
    <Spin spinning={globalLoading} size="large" tip="Loading..." wrapperClassName={'global-loading'}>
      <div className="app-container">
        <div className="content">
          <Outlet />
        </div>
        <div className="footer">
          <Link to={'/shop'} onClick={() => setSelectedButtonIndex(0)}>
            <RocketOutlined className={getFooterButtonClassname(0)} />
          </Link>
          <Link to={'/cart'} onClick={() => setSelectedButtonIndex(1)}>
            <ShoppingCartOutlined className={getFooterButtonClassname(1)} />
          </Link>
        </div>
      </div>
    </Spin>
  );
};
export default Layout;
