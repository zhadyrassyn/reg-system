import {
  SIGN_UP_FAILURE,
  SIGN_UP_SUCCESS,
  VERIFY_EMAIL_FAILURE,
  VERIFY_EMAIL_SUCCESS,
  RESEND_VERIFICATION_EMAIL_FAILURE,
  RESEND_VERIFICATION_EMAIL_SUCCESS,
  SIGN_IN_FAILURE,
  SIGN_IN_SUCCESS,
  SIGN_OUT,
  FETCH_CITIES_FAILURE,
  FETCH_CITIES_SUCCESS,
  FETCH_SCHOOLS_FAILURE,
  FETCH_SCHOOLS_SUCCESS,
  SAVE_STUDENT_PERSONAL_INFO_FAILURE,
  SAVE_STUDENT_PERSONAL_INFO_SUCCESS,
  SAVE_STUDENT_DOCUMENT_FAILURE,
  SAVE_STUDENT_DOCUMENT_SUCCESS,
  FETCH_STUDENT_DOCUMENTS_STATUS_FAILURE,
  FETCH_STUDENT_DOCUMENTS_STATUS_SUCCESS,
  FETCH_STUDENT_GENERAL_INFO_FAILURE,
  FETCH_STUDENT_GENERAL_INFO_SUCCESS,
  FETCH_STUDENTS_FAILURE,
  FETCH_STUDENTS_SUCCESS,
  FETCH_STUDENT_FULL_INFO_FAILURE,
  FETCH_STUDENT_FULL_INFO_SUCCESS,
  EDIT_STUDENT_GENERAL_INFO_FAILURE,
  EDIT_STUDENT_GENERAL_INFO_SUCCESS,
  SAVE_STUDENT_DOCUMENTS_COMMENT_FAILURE,
  SAVE_STUDENT_DOCUMENTS_COMMENT_SUCCESS,
  CHANGE_STUDENT_DOCUMENT_STATUS_FAILURE,
  CHANGE_STUDENT_DOCUMENT_STATUS_SUCCESS,
  FETCH_TOTAL_AMOUNT_OF_STUDENTS_FAILURE,
  FETCH_TOTAL_AMOUNT_OF_STUDENTS_SUCCESS,
  CHANGE_LANG,
  FETCH_AREAS_FAILURE,
  FETCH_AREAS_SUCCESS,
  SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_SUCCESS,
  SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_FAILURE,
  FETCH_STUDENT_PERSONAL_INFO_SUCCESS,
  FETCH_STUDENT_PERSONAL_INFO_FAILURE,
  FETCH_FACULTIES_FAILURE,
  FETCH_FACULTIES_SUCCESS,
  FETCH_SPECIALITIES_SUCCESS,
  FETCH_SPECIALITIES_FAILURE
} from "./types"

import axios from "axios"
import config from "../config"
import {browserHistory} from "react-router"
import {fetchIdFromToken} from "../utils"

export const changeLang = lang => ({type: CHANGE_LANG, lang})

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
      localStorage.setItem('token', response.data.token)

      dispatch({
        type: SIGN_IN_SUCCESS
      })

      browserHistory.push('/')

      if (onSuccess) {
        onSuccess()
      }
    })
    .catch(error => {
      const message = error.response && error.response.data && error.response.data.message
      dispatch({
        type: SIGN_IN_FAILURE,
        message
      })

      if (onError) {
        onError(message)
      }

      browserHistory.push('/signin')
    })
}

export const signOut = () => {
  localStorage.removeItem('token')
  return {
    type: SIGN_OUT
  }
}

export const fetchAreas = (onSucces, onError) => (dispatch) => {
  const request = `${config.url}/areas`

  axios.get(request)
    .then(({data}) => {
      dispatch({
        type: FETCH_AREAS_SUCCESS,
        data
      })
      if (onSucces) {
        onSucces()
      }
    })
    .catch(error => {
      dispatch({
        type: FETCH_AREAS_FAILURE,
        error: error.response && error.response.message
      })
      if (onError) {
        onError()
      }
    })
}

export const fetchFaculties = (onSucces, onError) => (dispatch) => {
  const request = `${config.url}/faculties`

  axios.get(request)
    .then(({data}) => {
      dispatch({
        type: FETCH_FACULTIES_SUCCESS,
        data
      })
      if (onSucces) {
        onSucces()
      }
    })
    .catch(error => {
      dispatch({
        type: FETCH_FACULTIES_FAILURE,
        error: error.response && error.response.message
      })
      if (onError) {
        onError()
      }
    })
}

export const fetchSpecialities = (facultyId, onSucces, onError) => (dispatch) => {
  const request = `${config.url}/faculties/${facultyId}/specialities`

  axios.get(request)
    .then(({data}) => {
      dispatch({
        type: FETCH_SPECIALITIES_SUCCESS,
        data
      })
      if (onSucces) {
        onSucces()
      }
    })
    .catch(error => {
      dispatch({
        type: FETCH_FACULTIES_FAILURE,
        error: error.response && error.response.message
      })
      if (onError) {
        onError()
      }
    })
}


export const fetchCities = (areaId, onSucces, onError) => (dispatch) => {
  const request = `${config.url}/areas/${areaId}/cities`

  axios.get(request)
    .then(({data}) => {
      dispatch({
        type: FETCH_CITIES_SUCCESS,
        data
      })
      if (onSucces) {
        onSucces()
      }
    })
    .catch(error => {
      dispatch({
        type: FETCH_CITIES_FAILURE,
        error: error.response && error.response.message
      })
      if (onError) {
        onError()
      }
    })
}

export const fetchSchools = (areaId, cityId, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/areas/${areaId}/cities/${cityId}/schools`

  axios.get(request)
    .then(({data}) => {
      dispatch({
        type: FETCH_SCHOOLS_SUCCESS,
        data
      })
      if (onSuccess) {
        onSuccess()
      }
    })
    .catch(error => {
      dispatch({
        type: FETCH_SCHOOLS_FAILURE,
        error: error.response && error.response.message
      })
      if (onError) {
        onError()
      }
    })
}

export const saveStudentPersonalInfo = (values, onSuccess, onError) => (dispatch) => {
  const token = localStorage.getItem('token')

  const userId = fetchIdFromToken(token)

  const request = `${config.url}/student/personal/${userId}`

  console.log('values', values)
  axios.post(request, values, {
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    }
  }).then(response => {
    dispatch({
      type: SAVE_STUDENT_PERSONAL_INFO_SUCCESS,
      values
    })

    if (onSuccess) {
      onSuccess()
    }
  }).catch(error => {
    dispatch({
      type: SAVE_STUDENT_PERSONAL_INFO_FAILURE,
      error: error.response && error.response.message
    })

    if (onError) {
      onError()
    }
  })
}

export const saveDocument = (file, documentType) => (dispatch) => {
  const token = localStorage.getItem('token')

  const userId = fetchIdFromToken(token)

  const request = `${config.url}/student/document/${userId}`

  const formData = new FormData()
  formData.append('file', file)
  formData.append('type', documentType)

  axios.post(request, formData, {
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-type': 'multipart/form-data',
    }
  }).then(response => {
    dispatch({
      type: SAVE_STUDENT_DOCUMENT_SUCCESS,
      documentType
    })
  })
    .catch(error =>
      dispatch({
        type: SAVE_STUDENT_DOCUMENT_FAILURE,
        message: error.response && error.response.message
      })
    )
}
export const savePersonalDocument = (file, documentType, onSuccess, onError) => (dispatch) => {
  const token = localStorage.getItem('token')

  const userId = fetchIdFromToken(token)

  const request = `${config.url}/student/personal/${userId}/document`

  const formData = new FormData()
  formData.append('file', file)
  formData.append('type', documentType)

  axios.post(request, formData, {
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-type': 'multipart/form-data',
    }
  }).then(response => {
    response.data.type = documentType

    dispatch({
      type: SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_SUCCESS,
      data: response.data
    })

    if (onSuccess) {
      onSuccess()
    }
  })
    .catch(error => {
        dispatch({
          type: SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_FAILURE,
          message: error.response && error.response.message
        })

        if (onError) {
          onError()
        }
      }
    )
}

export const fetchPersonalInfo = (onSuccess, onError) => (dispatch) => {
  const token = localStorage.getItem('token')

  const userId = fetchIdFromToken(token)

  const request = `${config.url}/student/personal/${userId}`

  axios.get(request, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(response => {
    dispatch({
      type: FETCH_STUDENT_PERSONAL_INFO_SUCCESS,
      data: response.data
    })

    if (onSuccess) {
      onSuccess(response.data)
    }
  }).catch(error => {
    dispatch({
      type: FETCH_STUDENT_PERSONAL_INFO_FAILURE,
      error: error.response && error.response.message
    })

    if (onError) {
      onError()
    }
  })
}

export const fetchDocumentsStatus = () => (dispatch) => {
  const token = localStorage.getItem('token')

  const userId = fetchIdFromToken(token)

  const request = `${config.url}/student/document/${userId}`

  axios.get(request, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(response => {
    dispatch({
      type: FETCH_STUDENT_DOCUMENTS_STATUS_SUCCESS,
      data: response.data
    })
  }).catch(error => {
    dispatch({
      type: FETCH_STUDENT_DOCUMENTS_STATUS_FAILURE,
      message: error.response && error.response.message
    })
  })
}

export const fetchStudentGeneralInfo = (onSuccess, onError) => (dispatch) => {
  const token = localStorage.getItem('token')

  const userId = fetchIdFromToken(token)

  const request = `${config.url}/student/general/${userId}`

  axios.get(request, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(({data}) => {
    dispatch({
      type: FETCH_STUDENT_GENERAL_INFO_SUCCESS,
      data
    })

    if (onSuccess) {
      onSuccess(data)
    }
  }).catch(error => {
    dispatch({
      type: FETCH_STUDENT_GENERAL_INFO_FAILURE,
      error: error.response && error.response.message
    })

    if (onError) {
      onError()
    }
  })
}

/* MODERATOR ACTIONS */
export const fetchStudents = (text, currentPage, perPage, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/moderator/students?currentPage=${currentPage}&perPage=${perPage}&text=${text}`

  const token = localStorage.getItem('token')
  axios.get(request, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(({data}) => {
    dispatch({
      type: FETCH_STUDENTS_SUCCESS,
      data
    })
    if (onSuccess) {
      onSuccess(data)
    }
  }).catch(error => {
    dispatch({
      type: FETCH_STUDENTS_FAILURE,
      error: error.response && error.response.message
    })
    if (onError) {
      onError()
    }
  })
}

export const fetchStudentFullInfo = (id, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/moderator/students/${id}`

  const token = localStorage.getItem('token')
  axios.get(request, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }).then(({data}) => {
    dispatch({
      type: FETCH_STUDENT_FULL_INFO_SUCCESS,
      data
    })
    if (onSuccess) {
      onSuccess(data)
    }
  }).catch(error => {
    dispatch({
      type: FETCH_STUDENT_FULL_INFO_FAILURE,
      error: error.response && error.response.message
    })
    if (onError) {
      onError()
    }
  })
}

export const editGeneralInfo = (id, generalInfoComment, generalInfoStatus, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/moderator/students/${id}/editGeneralInfo`

  const token = localStorage.getItem('token')
  const data = {
    comment: generalInfoComment,
    status: generalInfoStatus
  }
  axios.post(request, data, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(() => {
    dispatch({
      type: EDIT_STUDENT_GENERAL_INFO_SUCCESS,
      data
    })
    if (onSuccess) {
      onSuccess()
    }
  }).catch(error => {
    dispatch({
      type: EDIT_STUDENT_GENERAL_INFO_FAILURE,
      error: error.response && error.response.message
    })
    if (onError) {
      onError()
    }
  })
}

export const saveDocumentsComment = (id, comment, onSuccess, onError) => (dispatch) => {
  const request = `${config.url}/moderator/students/${id}/documents/comment`

  const token = localStorage.getItem('token')
  const data = {comment}
  axios.post(request, data, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(() => {
    dispatch({
      type: SAVE_STUDENT_DOCUMENTS_COMMENT_SUCCESS,
      data
    })
    if (onSuccess) {
      onSuccess()
    }
  }).catch(error => {
    dispatch({
      type: SAVE_STUDENT_DOCUMENTS_COMMENT_FAILURE,
      error: error.response && error.response.message
    })
    if (onError) {
      onError()
    }
  })
}

export const changeDocumentStatus = (id, document, onSuccess, onError) => (dispatch) => {
  console.log('document ', document)
  const request = `${config.url}/moderator/students/${id}/documents/${document.id}?status=${document.status}`

  const token = localStorage.getItem('token')

  axios.post(request, {}, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(() => {
    dispatch({
      type: CHANGE_STUDENT_DOCUMENT_STATUS_SUCCESS,
      data: document
    })
    // if(onSuccess) {
    //   onSuccess()
    // }
  }).catch(error => {
    dispatch({
      type: CHANGE_STUDENT_DOCUMENT_STATUS_FAILURE,
      error: error.response && error.response.message
    })
    // if(onError) {
    //   onError()
    // }
  })
}

export const fetchTotalAmountOfStudents = (text, onSuccess, onError) => (dispatch) => {
  console.log('document ', document)
  const request = `${config.url}/moderator/students/total?text=${text}`

  const token = localStorage.getItem('token')

  axios.get(request, {
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  }).then(({data}) => {
    dispatch({
      type: FETCH_TOTAL_AMOUNT_OF_STUDENTS_SUCCESS,
      data
    })
    if (onSuccess) {
      onSuccess()
    }
  }).catch(error => {
    dispatch({
      type: FETCH_TOTAL_AMOUNT_OF_STUDENTS_FAILURE,
      error: error.response && error.response.message
    })
    if (onError) {
      onError()
    }
  })
}