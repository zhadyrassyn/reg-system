import React, { Component } from "react"

import {
  ACCEPTED,
  REJECTED
} from "../../constants"

class EditStudentGeneralInfo extends Component {

  constructor(props) {
    super(props)

    this.state = {
      comment: ""
    }
  }

  componentWillReceiveProps(nextProps) {
    console.log('nextprops')
    console.log('gj ' , nextProps.selectedStudent.generalInfoComment)
    this.setState({comment: nextProps.selectedStudent.generalInfoComment})
  }

  onGeneralInfoEdit = (status) => {
    this.props.onGeneralInfoEdit(this.state.comment, status)
  }

  onTextAreaChange = (event) => {
    this.setState({comment: event.target.value})
  }

  render() {
    const {
      id, firstName, middleName, lastName, email, birthDate, city, school, documentsComment,
      generalInfoComment, generalInfoStatus,
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
          <button className="btn btn-outline-success" onClick={this.onGeneralInfoEdit.bind(this, ACCEPTED)}>User info seems correct</button>
          <button className="btn btn-outline-danger ml-2" onClick={this.onGeneralInfoEdit.bind(this, REJECTED)}>User info seems wrong</button>
        </div>
      </div>
    )
  }
}

export default EditStudentGeneralInfo