import { handlers } from './Handlers.ts';
import { setupWorker } from 'msw';

export const server = setupWorker(...handlers);
