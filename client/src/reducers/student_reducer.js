import {
  SAVE_STUDENT_DOCUMENT_FAILURE,
  SAVE_STUDENT_DOCUMENT_SUCCESS,
  FETCH_STUDENT_DOCUMENTS_STATUS_FAILURE,
  FETCH_STUDENT_DOCUMENTS_STATUS_SUCCESS,
  FETCH_STUDENT_GENERAL_INFO_FAILURE,
  FETCH_STUDENT_GENERAL_INFO_SUCCESS
} from "../actions/types"

import {
  WAITING_FOR_RESPONSE,
  ACCEPTED,
  REJECTED
} from "../constants/index"

const initialState = {
  documentsStatus: {},
  studentInfo: {}
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
    default:
      return state
  }
}