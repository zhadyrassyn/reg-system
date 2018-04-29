import _ from "lodash"
import {
  FETCH_CITIES_SUCCESS,
  FETCH_CITIES_FAILURE,
  FETCH_SCHOOLS_FAILURE,
  FETCH_SCHOOLS_SUCCESS,
  FETCH_AREAS_FAILURE,
  FETCH_AREAS_SUCCESS,
  FETCH_FACULTIES_SUCCESS,
  FETCH_FACULTIES_FAILURE,
  FETCH_SPECIALITIES_SUCCESS,
  FETCH_SPECIALITIES_FAILURE
} from "../actions/types"

const initialState = {
  areas: {},
  cities: {},
  schools: {},
  faculties: {},
  specialities: {}
}

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_AREAS_SUCCESS:
      return {
        ...state,
        areas: _.mapKeys(action.data, "id")
      }
    case FETCH_AREAS_FAILURE:
      return {
        ...state,
        error: action.message
      }
    case FETCH_CITIES_SUCCESS:
      return {
        ...state,
        cities: _.mapKeys(action.data, "id")
      }
    case FETCH_CITIES_FAILURE:
      return {
        ...state,
        error: action.message
      }
    case FETCH_SCHOOLS_SUCCESS:
      return {
        ...state,
        schools: _.mapKeys(action.data, "id")
      }
    case FETCH_SCHOOLS_FAILURE:
      return {
        ...state,
        error: action.message
      }
    case FETCH_FACULTIES_SUCCESS:
      return {
        ...state,
        faculties: _.mapKeys(action.data, "id")
      }
    case FETCH_FACULTIES_FAILURE:
      return {
        ...state,
        error: action.message
      }
    case FETCH_SPECIALITIES_SUCCESS:
      return {
        ...state,
        specialities: _.mapKeys(action.data, "id")
      }
    case FETCH_SPECIALITIES_FAILURE:
      return {
        ...state,
        error: action.message
      }
    default:
      return state
  }
}