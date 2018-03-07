import {
  SIGN_UP_FAILURE,
  SIGN_UP_SUCCESS
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
    default:
      return state
  }
}