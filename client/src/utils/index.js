import jwtDecode from "jwt-decode"

export const fetchIdFromToken = (token) => {
  return jwtDecode(token).id
}