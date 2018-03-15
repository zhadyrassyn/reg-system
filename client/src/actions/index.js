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
} from "./types"

import axios from "axios"
import jwtDecode from "jwt-decode"
import config from "../config"
import { browserHistory } from "react-router"

export const singUp = ({email, password}, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/auth/signup`

  axios.post(request, {email, password})
    .then(response => {
      dispatch({
        type: SIGN_UP_SUCCESS
      })
      onSuccess()
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
      dispatch({
        type: VERIFY_EMAIL_SUCCESS
      })
      localStorage.setItem('token', token)
      browserHistory.push('/')
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
      dispatch({
        type: SIGN_IN_SUCCESS
      })
      localStorage.setItem('token', response.data.token)

      //check role
      const token = response.data.token
      const decoded = jwt_decode(token)
      console.log('Decoded ', decoded.sub)


      browserHistory.push('/')
      onSuccess()
    })
    .catch(error => {
      const message = error.response && error.response.data && error.response.data.message
      dispatch({
        type: SIGN_IN_FAILURE,
        message
      })
      onError(message)
      browserHistory.push('/signin')

    })
}

export const signOut = () => {
  localStorage.removeItem('token')
  return {
    type: SIGN_OUT
  }
}