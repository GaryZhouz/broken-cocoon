import { createBrowserRouter } from 'react-router-dom';
import NotFoundPage from '../pages/404/index.tsx';
import Register from '../pages/Register.tsx';
import Layout from '../pages/shop/Layout.tsx';
import Shop from '../pages/shop/Shop.tsx';
import Cart from '../pages/shop/Cart.tsx';

const router = createBrowserRouter([
  // {
  //   path: '/',
  //   element: <App />,
  //   children: [
  //     {
  //       path: '/message/:id?',
  //       element: <Message />,
  //     },
  //   ],
  // },

  {
    path: '/',
    element: <Layout />,
    children: [
      {
        path: '/shop',
        element: <Shop />,
      },
      {
        path: '/cart',
        element: <Cart />,
      },
    ],
  },
  {
    path: '/register',
    element: <Register />,
  },
  {
    path: '*',
    element: <NotFoundPage />,
  },
]);

export default router;
