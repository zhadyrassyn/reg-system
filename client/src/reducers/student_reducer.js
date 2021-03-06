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
  FETCH_EDUCATION_INFO_FAILURE,
  SAVE_EDUCATION_DOCUMENT_SUCCESS,
  SAVE_EDUCATION_DOCUMENT_FAILURE,
  SAVE_MEDICAL_DOCUMENT_FAILURE,
  SAVE_MEDICAL_DOCUMENT_SUCCESS,
  FETCH_MEDICAL_INFO_SUCCESS,
  FETCH_MEDICAL_INFO_FAILURE
} from "../actions/types"

import {
  WAITING_FOR_RESPONSE,
  ACCEPTED,
  REJECTED,

  PHOTO_3x4,
  IDENTITY_CARD_BACK,
  IDENTITY_CARD_FRONT,
  DIPLOMA_CERTIFICATE,
  UNT_CT_CERTIFICATE,
  HEALTH_063,
  HEALTH_086,
  FLUOROGRAPHY
} from "../constants"

const initialState = {
  documentsStatus: {},
  studentInfo: {},
  personalInfo: {},
  personalInfoDocuments:{},
  educationInfo: {},
  educationInfoDocuments: {},
  medicalInfoDocuments: {}
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

    case SAVE_MEDICAL_DOCUMENT_SUCCESS:
      const medicalDocType = action.data.type
      const medicalDocName = action.data.name
      if(medicalDocType === HEALTH_086) {
        return {
          ...state,
          medicalInfoDocuments: {
            ...state.medicalInfoDocuments,
            form86: medicalDocName
          }
        }
      } else if(medicalDocType === HEALTH_063) {
        return {
          ...state,
          medicalInfoDocuments: {
            ...state.medicalInfoDocuments,
            form63: medicalDocName
          }
        }
      } else if(medicalDocType === FLUOROGRAPHY) {
        return {
          ...state,
          medicalInfoDocuments: {
            ...state.medicalInfoDocuments,
            flurography: medicalDocName
          }
        }
      } else {
        return {
          ...state
        }
      }
    case SAVE_MEDICAL_DOCUMENT_FAILURE:
      return {
        ...state,
        error: action.error
      }

    case FETCH_MEDICAL_INFO_SUCCESS:
      return {
        ...state,
        medicalInfoDocuments: action.data
      }
    case FETCH_MEDICAL_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }

    case SAVE_EDUCATION_DOCUMENT_SUCCESS:
      const educationDocType = action.data.type
      const educationDocName = action.data.name
      if (educationDocType === DIPLOMA_CERTIFICATE) {
        return {
          ...state,
          educationInfoDocuments: {
            ...state.educationInfoDocuments,
            schoolDiploma: educationDocName
          }
        }
      } else if(educationDocType === UNT_CT_CERTIFICATE) {
        return {
          ...state,
          educationInfoDocuments: {
            ...state.educationInfoDocuments,
            entCertificate: educationDocName
          }
        }
      } else {
        return {
          ...state
        }
      }

    case SAVE_EDUCATION_DOCUMENT_FAILURE:
      return {
        ...state,
        error: action.error
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