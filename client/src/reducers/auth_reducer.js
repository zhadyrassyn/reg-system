import {
  SIGN_UP_FAILURE,
  SIGN_UP_SUCCESS,
  VERIFY_EMAIL_FAILURE,
  VERIFY_EMAIL_SUCCESS,
  RESEND_VERIFICATION_EMAIL_FAILURE,
  RESEND_VERIFICATION_EMAIL_SUCCESS,
  SIGN_IN_FAILURE,
  SIGN_IN_SUCCESS,
  SIGN_OUT
} from "../actions/types"

const initialState = {
  authenticated: false,
  signInFailed: false
}
export default (state = {initialState}, action) => {
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
        error: action.message,
        authenticated: false
      }
    case RESEND_VERIFICATION_EMAIL_FAILURE:
      return {
        ...state,
        error: action.message,
      }
    case RESEND_VERIFICATION_EMAIL_SUCCESS:
      return {
        ...state
      }
    case SIGN_IN_FAILURE:
      return {
        ...state,
        signInFailed: true,
        error: action.message
      }
    case SIGN_IN_SUCCESS:
      return {
        ...state,
        authenticated: true
      }
    case SIGN_OUT:
      return {
        ...state,
        authenticated: false,
        signInFailed: true
      }
    default:
      return state
  }
}