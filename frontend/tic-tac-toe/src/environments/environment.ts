export const environment = {
  DefaultLanguage: "en",
  production: true,
  development: false,
  environmentName:"PROD",
  // @ts-ignore
  baseURL: window['env']['BASE_URL'] || 'http://localhost:8080/',
  // @ts-ignore
  websocket: window['env']['BASE_WEBSOCKET'] || 'ws://localhost:8080/websocket/',
  LOCAL_STORAGE_USER_ID: "TIC_TAC_TOE_USER_ID",
  LOCAL_STORAGE_USERNAME: "TIC_TAC_TOE_USERNAME",
  LOCAL_STORAGE_USER_TOKEN: "TIC_TAC_TOE_TOKEN"
};
