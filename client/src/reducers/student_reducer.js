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
  FETCH_STUDENT_PERSONAL_INFO_SUCCESS,
  SAVE_EDUCATION_INFO_SUCCESS,
  SAVE_EDUCATION_INFO_FAILURE,
  FETCH_EDUCATION_INFO_SUCCESS,
  FETCH_EDUCATION_INFO_FAILURE
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
  personalInfo: {},
  personalInfoDocuments:{},
  educationInfo: {},
  educationInfoDocuments: {}
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
          personalInfoDocuments: {
            ...state.personalInfoDocuments,
            photo3x4: name
          }
        }
      } else if(type === IDENTITY_CARD_FRONT) {
        return {
          ...state,
          personalInfoDocuments: {
            ...state.personalInfoDocuments,
            ud_front: name
          }
        }
      } else if(type === IDENTITY_CARD_BACK) {
        return {
          ...state,
          personalInfoDocuments: {
            ...state.personalInfoDocuments,
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
        personalInfo: action.data,
        personalInfoDocuments: {
          ...state.personalInfoDocuments,
          photo3x4: action.data.photo3x4,
          ud_front: action.data.ud_front,
          ud_back: action.data.ud_back
        }
      }
    case FETCH_STUDENT_PERSONAL_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case FETCH_EDUCATION_INFO_SUCCESS:
      return {
        ...state,
        educationInfo: action.data,
        educationInfoDocuments: {
          ...state.educationInfoDocuments,
          schoolDiploma: action.data.schoolDiploma,
          entCertificate: action.data.entCertificate
        }
      }
    case FETCH_EDUCATION_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case SAVE_EDUCATION_INFO_SUCCESS:
      return {
        ...state,
        educationInfo: action.data
      }
    case SAVE_EDUCATION_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    default:
      return state
  }
}