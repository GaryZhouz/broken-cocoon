import React from 'react';
import ReactDOM from 'react-dom/client';
import Register from './pages/Register.tsx';
import { server } from './mocks/MockServer.ts';

server.start();

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <Register />
  </React.StrictMode>,
);
