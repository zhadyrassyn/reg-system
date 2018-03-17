import { combineReducers } from "redux"
import { reducer as formReducer } from "redux-form"
import authReducer from "./auth_reducer"
import infoReducer from "./info_reducer"

const rootReducer = combineReducers({
  form: formReducer,
  auth: authReducer,
  info: infoReducer
});

export default rootReducer
