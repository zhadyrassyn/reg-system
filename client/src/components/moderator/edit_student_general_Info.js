import React, { Component } from "react"

import {
  ACCEPTED,
  REJECTED
} from "../../constants"

class EditStudentGeneralInfo extends Component {

  constructor(props) {
    super(props)

    this.state = {
      comment: this.props.selectedStudent.generalInfoComment
    }
  }

  onGeneralInfoEdit = (status) => {
    this.props.onGeneralInfoEdit(this.state.comment, status)
  }

  onTextAreaChange = (event) => {
    this.setState({comment: event.target.value})
  }

  render() {
    const {
      id, firstName, middleName, lastName, email, birthDate, city, school, generalInfoStatus,
    } = this.props.selectedStudent

    const comment = this.state.comment
    console.log('comment ', comment)

    return (
      <div>
        <h3>General info</h3>
        <p>{ firstName } { middleName } { lastName }, { birthDate }. Status: Accept</p>
        <p>City: { city }. Школа: { school }</p>
        <p>Email: { email }</p>
        <div className="form-group">
          <label htmlFor="generalInfoComment"><strong>Leave comment</strong></label>
          <textarea className="form-control" id="generalInfoComment" rows="3" placeholder="Leave comment..." defaultValue={ comment }
          onChange={this.onTextAreaChange}/>
        </div>
        <div>
          <button className={"btn " + (generalInfoStatus === ACCEPTED ? " btn-success" : "btn-outline-success")} onClick={this.onGeneralInfoEdit.bind(this, ACCEPTED)}>User info seems correct</button>
          <button className={"btn ml-2 " + (generalInfoStatus === REJECTED ? " btn-danger" : "btn-outline-danger")} onClick={this.onGeneralInfoEdit.bind(this, REJECTED)}>User info seems wrong</button>
        </div>
      </div>
    )
  }
}

export default EditStudentGeneralInfo