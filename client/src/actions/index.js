import {
  SIGN_UP_FAILURE,
  SIGN_UP_SUCCESS,
  VERIFY_EMAIL_FAILURE,
  VERIFY_EMAIL_SUCCESS,
  RESEND_VERIFICATION_EMAIL_FAILURE,
  RESEND_VERIFICATION_EMAIL_SUCCESS,
  SIGN_IN_FAILURE,
  SIGN_IN_SUCCESS
} from "./types"

import axios from "axios"
import config from "../config"
import { browserHistory } from "react-router"

export const singUp = ({email, password}, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/auth/signup`

  axios.post(request, {email, password})
    .then(response => {
      onSuccess()
      dispatch({
        type: SIGN_UP_SUCCESS
      })
    })
    .catch(error => {
      onError(error.response)
      dispatch({
        type: SIGN_UP_FAILURE,
        message: error.response && error.response.message
      })
    })
}

export const verifyEmail = (token) => (dispatch) => {
  const request = `${config.url}/auth/token/${token}`

  axios.post(request)
    .then(response => {
      browserHistory.push('/')
      dispatch({
        type: VERIFY_EMAIL_SUCCESS
      })
    })
    .catch(error => {
      dispatch({
        type: VERIFY_EMAIL_FAILURE,
        message: error.response && error.response.message
      })
    })
}

export const resendVerificationEmail = (email, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/auth/resend?email=${email}`

  axios.post(request)
    .then(response => {
      onSuccess()
      dispatch({
        type: RESEND_VERIFICATION_EMAIL_SUCCESS
      })
    })
    .catch(error => {
      onError()
      dispatch({
        type: RESEND_VERIFICATION_EMAIL_FAILURE,
        message: error.response && error.response.message
      })
    })
}

export const signIn = (values, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/auth/signin`

  axios.post(request, values)
    .then(response => {
      //onSuccess()
      dispatch({
        type: SIGN_IN_SUCCESS
      })
    })
    .catch(error => {
      onError()
      browserHistory.push('/signin')
      dispatch({
        type: SIGN_IN_FAILURE,
        message: error.response && error.response.data && error.response.data.message
      })
    })
}