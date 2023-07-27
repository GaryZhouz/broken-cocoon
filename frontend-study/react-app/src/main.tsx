import React from 'react';
import ReactDOM from 'react-dom/client';
import { server } from './mocks/MockServer.ts';
import { RouterProvider } from 'react-router-dom';
import router from './router';
import { RecoilRoot } from 'recoil';

server.start();

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RecoilRoot>
      <RouterProvider router={router} />
    </RecoilRoot>
  </React.StrictMode>,
);
