import {
  SAVE_STUDENT_DOCUMENT_FAILURE,
  SAVE_STUDENT_DOCUMENT_SUCCESS,
  FETCH_STUDENT_DOCUMENTS_STATUS_FAILURE,
  FETCH_STUDENT_DOCUMENTS_STATUS_SUCCESS,
  FETCH_STUDENT_GENERAL_INFO_FAILURE,
  FETCH_STUDENT_GENERAL_INFO_SUCCESS,
  SAVE_STUDENT_PERSONAL_INFO_FAILURE,
  SAVE_STUDENT_PERSONAL_INFO_SUCCESS,
  SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_FAILURE,
  SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_SUCCESS,
  FETCH_STUDENT_PERSONAL_INFO_FAILURE,
  FETCH_STUDENT_PERSONAL_INFO_SUCCESS
} from "../actions/types"

import {
  WAITING_FOR_RESPONSE,
  ACCEPTED,
  REJECTED,

  PHOTO_3x4,
  IDENTITY_CARD_BACK,
  IDENTITY_CARD_FRONT
} from "../constants"

const initialState = {
  documentsStatus: {},
  studentInfo: {},
  personalInfo: {}
}

export default (state = initialState, action) => {
  switch (action.type) {
    case SAVE_STUDENT_DOCUMENT_SUCCESS:
      return {
        ...state,
        documentsStatus: {
          ...state.documentsStatus,
          [action.documentType]: WAITING_FOR_RESPONSE
        }
      }
    case SAVE_STUDENT_DOCUMENT_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case FETCH_STUDENT_DOCUMENTS_STATUS_SUCCESS:
      const copy =  {
        ...state,
        documentsStatus: {
          ...state.documentsStatus,
        },
      }

      action.data.forEach(document => copy.documentsStatus[document.type] = document.status)

      return copy
    case FETCH_STUDENT_DOCUMENTS_STATUS_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case FETCH_STUDENT_GENERAL_INFO_SUCCESS:
      return {
        ...state,
        studentInfo: action.data
      }
    case SAVE_STUDENT_PERSONAL_INFO_SUCCESS:
      return {
        ...state
      }
    case SAVE_STUDENT_PERSONAL_INFO_FAILURE:
      return {
        ...state
      }
    case SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_SUCCESS:
      const {type, name} = action.data
      if(type === PHOTO_3x4) {
        return {
          ...state,
          personalInfo: {
            ...state.personalInfo,
            photo3x4: name
          }
        }
      } else if(type === IDENTITY_CARD_FRONT) {
        return {
          ...state,
          personalInfo: {
            ...state.personalInfo,
            ud_front: name
          }
        }
      } else if(type === IDENTITY_CARD_BACK) {
        return {
          ...state,
          personalInfo: {
            ...state.personalInfo,
            ud_back: name
          }
        }
      } else {
        return {
          ...state
        }
      }
    case SAVE_STUDENT_PERSONAL_INFO_DOCUMENT_FAILURE:
      return {
        ...state
      }
    case FETCH_STUDENT_PERSONAL_INFO_SUCCESS:
      return {
        ...state,
        personalInfo: action.data
      }
    case FETCH_STUDENT_PERSONAL_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    default:
      return state
  }
}