import React, { Component } from "react"
import _ from "lodash"

const documents = [
  {
    type: DIPLOMA_CERTIFICATE,
    text: 'Certificate of secondary education completion or the original diploma of technical and vocational (primary or secondary vocational, post-secondary) education'
  },
  {
    type: UNT_CT_CERTIFICATE,
    text: 'Certificate of UNT / CT'
  },
  {
    type: PHOTO_3x4,
    text: 'Photo 3x4',
  },
  {
    type: HEALTH_086,
    text: 'Health certificate 086-U'
  },
  {
    type: HEALTH_063,
    text: 'Health certificate 0-63'
  },
  {
    type: FLUOROGRAPHY,
    text: 'fluorography'
  },
  {
    type: IDENTITY_CARD_FRONT,
    text: 'A copy of the identity card (front side)'
  },
  {
    type: IDENTITY_CARD_BACK,
    text: 'A copy of the identity card (back side)'
  }
]

import {
  DIPLOMA_CERTIFICATE,
  UNT_CT_CERTIFICATE,
  PHOTO_3x4,
  HEALTH_086,
  HEALTH_063,
  FLUOROGRAPHY,
  IDENTITY_CARD_FRONT,
  IDENTITY_CARD_BACK,

  WAITING_FOR_RESPONSE,
  ACCEPTED,
  REJECTED,
  NOT_SEND

} from "../../constants"

import config from "../../config"

class EditStudentDocuments extends Component {
  renderDocuments(userDocuments) {
    return _.map(
      documents, document => {
        const option1 = document.type + ACCEPTED
        const option2 = document.type + REJECTED
        const option3 = document.type + WAITING_FOR_RESPONSE
        const option4 = document.type + NOT_SEND

        const path = `${config.url}/upload/${userDocuments[document.type].url}`

        return (
          <li key={ document.type } className="list-group-item">
            <p><a href={path} target="_blank">{ document.text }</a></p>
            <div className="form-check form-check-inline ml-2">
              <div className="form-check form-check-inline">
                <input className="form-check-input" type="radio" name={ option1 } id={ option1 } value={ option1 }
                       defaultChecked={ userDocuments[document.type].status === ACCEPTED}/>
                <label className="form-check-label text-success" htmlFor={ option1 }>{ ACCEPTED }</label>
              </div>
              <div className="form-check form-check-inline">
                <input className="form-check-input" type="radio" name={ option2 } id={ option2 } value={ option2 }
                       defaultChecked={ userDocuments[document.type].status === REJECTED}/>
                <label className="form-check-label text-danger" htmlFor={ option2 }>{ REJECTED }</label>
              </div>
              <div className="form-check form-check-inline">
                <input className="form-check-input" type="radio" name={ option3 } id={ option3 } value={ option3 }
                       defaultChecked={ userDocuments[document.type].status === WAITING_FOR_RESPONSE}/>
                <label className="form-check-label text-warning" htmlFor={ option3 }>{ WAITING_FOR_RESPONSE }</label>
              </div>
              <div className="form-check form-check-inline">
                <input className="form-check-input" type="radio" name={ option4 } id={ option4 } value={ option4 }
                       defaultChecked={ userDocuments[document.type].status === NOT_SEND}/>
                <label className="form-check-label text-info" htmlFor={ option4 }>{ NOT_SEND }</label>
              </div>
            </div>
          </li>
        )
      }
    )
  }

  render() {
    const {
      id, firstName, middleName, lastName, email, birthDate, city, school, documentsComment,
      generalInfoComment, generalInfoStatus,
    } = this.props.selectedStudent
    const documents = _.mapKeys(this.props.selectedStudent.documents, "type")

    return (
      <div>
        <h3>Documents</h3>
        <ol className="list-group">
          {this.renderDocuments(documents)}
        </ol>
        <div className="form-group">
          <label htmlFor="documentsComment"><strong>Leave comment</strong></label>
          <textarea className="form-control" id="documentsComment" rows="3" placeholder="Leave comment..." defaultValue={ generalInfoComment }/>
        </div>
        <div>
          <button className="btn btn-outline-info">Save comment for documents</button>
        </div>
      </div>
    )
  }
}

export default EditStudentDocuments