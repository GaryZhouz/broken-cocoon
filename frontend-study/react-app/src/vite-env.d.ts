/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_SEVER_URI: string;
  readonly VITE_SEVER_TIMEOUT: number;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
