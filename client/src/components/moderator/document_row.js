import React, { Component } from "react"

import {
  WAITING_FOR_RESPONSE,
  ACCEPTED,
  REJECTED,
  NOT_SEND
} from "../../constants"

class DocumentRow extends Component {
  constructor(props) {
    super(props)

    this.state = {
      selectedOption: this.props.status
    }

  }

  handleOptionChange = (event) => {
    const document = this.props.document
    document.status = event.target.value

    this.props.onDocumentStatusChange(document,
      () => {
        this.setState({ selectedOption: event.target.value })
      },
      () => {
        console.log('error callback')
      })

  }

  render() {
    const { path, status, title, id } = this.props
    const { selectedOption } = this.state

    const option1 = id + ACCEPTED
    const option2 = id + REJECTED
    const option3 = id + WAITING_FOR_RESPONSE
    const option4 = id + NOT_SEND

    return (
      <li  className="list-group-item">
        <p><a href={path} target="_blank">{ title }</a></p>
        <div className="form-check form-check-inline ml-2">
          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name={ id } id={ option1 } value={ ACCEPTED }
                   defaultChecked={ selectedOption === ACCEPTED}
                   onChange={this.handleOptionChange.bind(this)}
            />
            <label className="form-check-label text-success" htmlFor={ option1 }>{ ACCEPTED }</label>
          </div>

          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name={ id } id={ option2 } value={ REJECTED }
                   defaultChecked={ selectedOption === REJECTED}
                   onChange={this.handleOptionChange.bind(this)}
            />
            <label className="form-check-label text-danger" htmlFor={ option2 }>{ REJECTED }</label>
          </div>

          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name={ id } id={ option3 } value={ WAITING_FOR_RESPONSE }
                   defaultChecked={ selectedOption === WAITING_FOR_RESPONSE}
                   onChange={this.handleOptionChange.bind(this)}
            />
            <label className="form-check-label text-warning" htmlFor={ option3 }>{ WAITING_FOR_RESPONSE }</label>
          </div>

          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name={ id } id={ option4 } value={ NOT_SEND }
                   defaultChecked={selectedOption === NOT_SEND}
                   onChange={this.handleOptionChange.bind(this)}
            />
            <label className="form-check-label text-info" htmlFor={ option4 }>{ NOT_SEND }</label>
          </div>
        </div>
      </li>
    )
  }
}

export default DocumentRow