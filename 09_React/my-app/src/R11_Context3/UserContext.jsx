import React, { createContext } from 'react';

/*
  Context 객체를 어디서든 사용할 수 있게 별도 폴더에 생성 후 export
  ==> 필요한 파일에서 import 해서 사용
*/

const UserContext = createContext();

export default UserContext