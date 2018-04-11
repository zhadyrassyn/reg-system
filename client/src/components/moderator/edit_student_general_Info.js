import React, { Component } from "react"

class EditStudentGeneralInfo extends Component {
  render() {
    const {
      id, firstName, middleName, lastName, email, birthDate, city, school, documentsComment,
      generalInfoComment, generalInfoStatus,
    } = this.props.selectedStudent

    return (
      <div>
        <h3>General info</h3>
        <p>{ firstName } { middleName } { lastName }, { birthDate }. Status: Accept</p>
        <p>City: { city }. Школа: { school }</p>
        <p>Email: { email }</p>
        <div className="form-group">
          <label htmlFor="generalInfoComment"><strong>Leave comment</strong></label>
          <textarea className="form-control" id="generalInfoComment" rows="3" placeholder="Leave comment..." defaultValue={ generalInfoComment }/>
        </div>
        <div>
          <button className="btn btn-outline-success">User info seems correct</button>
          <button className="btn btn-outline-danger ml-2">User info seems wrong</button>
        </div>
      </div>
    )
  }
}

export default EditStudentGeneralInfo