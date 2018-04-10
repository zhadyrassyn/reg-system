import React, { Component } from "react"

class EditStudent extends Component {
  render() {
    const { closeModal, selectedStudent } = this.props
    const {
      id, firstName, middleName, lastName, email, birthDate, city, school, documents, documentsComment,
      generalInfoComment, generalInfoStatus,
    } = selectedStudent

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-12">
            <button onClick={closeModal} className="close" type="button"/>
            <h3>General info</h3>
            <p>{ firstName } { middleName } { lastName }, { birthDate }. Status: Accept</p>
            <p>City: { city }. Школа: { school }</p>
            <p>Email: { email }</p>
            <div className="form-group">
              <label htmlFor="generalInfoComment"><strong>Leave comment</strong></label>
              <textarea className="form-control" id="generalInfoComment" rows="3" placeholder="Leave comment...">{ generalInfoComment }</textarea>
            </div>
            <div>
              <button className="btn btn-outline-success width120">Accept</button>
              <button className="btn btn-outline-danger width120 ml-2">Reject</button>
            </div>
          </div>
        </div>

        <div className="row mt-3">
          <div className="col-md-12">
            <h3>Documents</h3>
            <ol>
              <li>
                <span>Document</span>
                <div className="form-check form-check-inline ml-2">
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                  </div>
                </div>
              </li>
              <li>
                <span>Document</span>
                <div className="form-check form-check-inline ml-2">
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                  </div>
                </div>
              </li>
              <li>
                <span>Document</span>
                <div className="form-check form-check-inline ml-2">
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                  </div>
                </div>
              </li>
              <li>
                <span>Document</span>
                <div className="form-check form-check-inline ml-2">
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                  </div>
                </div>
              </li>
              <li>
                <span>Document</span>
                <div className="form-check form-check-inline ml-2">
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                  </div>
                </div>
              </li>
              <li>
                <span><a href="#" target="_blank">Document</a></span>
                <div className="form-check form-check-inline ml-2">
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                  </div>
                </div>
              </li>
            </ol>
            <div className="form-group">
              <label htmlFor="documentsComment"><strong>Leave comment</strong></label>
              <textarea className="form-control" id="documentsComment" rows="3" placeholder="Leave comment...">{ documentsComment }</textarea>
            </div>
            <div>
              <button className="btn btn-outline-success width120">Accept</button>
              <button className="btn btn-outline-danger width120 ml-2">Reject</button>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default EditStudent