import React, { Component } from 'react'
import "./overview_menu"
import OverviewMenu from "./overview_menu"
import Select from "react-select"
// import

class StudentApp extends Component {
  constructor(props) {
    super(props)

    this.state = {
      city: '',
      school: ''
    }
  }

  handleCityChange = (city) => {
    this.setState({ city });
    console.log(`Selected: ${city.label}`);
  }

  handleSchoolChange = (school) => {
    this.setState({ school })
    console.log(`Selected school: ${school.label}`)
  }

  render() {
    const { city, school } = this.state

    const cityValue = city && city.value
    const schoolValue = school && school.value

    return (
      <div className="container">
        <OverviewMenu/>
        <form>
          <div className="form-row">
            <div className="col">
              <label htmlFor="firstName">First name</label>
              <input type="text" className="form-control" placeholder="First name" id="firstName"/>
            </div>
            <div className="col">
              <label htmlFor="middleName">Middle name</label>
              <input type="text" className="form-control" placeholder="Middle name" id="middleName"/>
            </div>
            <div className="col">
              <label htmlFor="lastName">Last name</label>
              <input type="text" className="form-control" placeholder="Last name" id="lastName"/>
            </div>
          </div>
          <div className="form-row mt-3">
            <div className="col">
              <label htmlFor="birthDate">Date of birth</label>
              <input type="date" className="form-control" id="birthDate"/>
            </div>
          </div>
          <div className="form-row mt-3">
            <div className="col">
              <label htmlFor="city">City</label>
              <Select
                name="city"
                value={cityValue}
                onChange={this.handleCityChange}
                options={[
                  { value: "1", label: "Almaty"},
                  { value: "2", label: "Astana"}
                ]}
              />
            </div>
          </div>
          <div className="form-row mt-3">
            <div className="col">
              <label htmlFor="school">School</label>
              <Select
                name="school"
                value={schoolValue}
                onChange={this.handleSchoolChange}
                options={[
                  { value: "1", label: "3 school" },
                  { value: "2", label: "2 school" }
                ]}
              />
            </div>
          </div>
        </form>
      </div>
    )
  }
}

export default StudentApp