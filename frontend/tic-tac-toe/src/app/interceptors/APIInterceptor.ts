import {HttpInterceptorFn} from '@angular/common/http';
import {environment} from "../../environments/environment";

export const APIInterceptor: HttpInterceptorFn = (req, next) => {
  const apiReq = req.clone({ url: environment.baseURL+`${req.url}` });
  return next(apiReq);
};
