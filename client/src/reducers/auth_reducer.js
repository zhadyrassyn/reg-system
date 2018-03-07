import {
  SIGN_UP_FAILURE,
  SIGN_UP_SUCCESS,
  VERIFY_EMAIL_FAILURE,
  VERIFY_EMAIL_SUCCESS
} from "../actions/types"

export default (state = {}, action) => {
  switch (action.type) {
    case SIGN_UP_SUCCESS:
      return {
        ...state,
        error: '',
        authenticated: false
      }
    case SIGN_UP_FAILURE:
      return {
        ...state,
        error: action.message,
        authenticated: false
      }
    case VERIFY_EMAIL_SUCCESS:
      return {
        ...state,
        authenticated: true
      }
    case VERIFY_EMAIL_FAILURE:
      return {
        ...state,
        authenticated: false
      }
    default:
      return state
  }
}