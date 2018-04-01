import { combineReducers } from "redux"
import { reducer as formReducer } from "redux-form"
import authReducer from "./auth_reducer"
import infoReducer from "./info_reducer"
import studentReducer from "./student_reducer"
import moderatorReducer from "./moderator_reducer"

const rootReducer = combineReducers({
  form: formReducer,
  auth: authReducer,
  info: infoReducer,
  student: studentReducer,
  moderator: moderatorReducer
});

export default rootReducer
