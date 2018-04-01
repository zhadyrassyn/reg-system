import React, {Component} from 'react'

class ModeratorApp extends Component {

  componentWillMount() {
    document.body.style.backgroundColor = "#F8F6F9";
  }
  componentWillUnmount() {
    document.body.style.backgroundColor = null;
  }

  render() {
    return (
      <div className="wrapper">
        <nav className="navbar justify-content-between py-4">
          <div className="container">
            <form className="form-inline" id="search-form">
              <input type="text" className="form-control" placeholder="Search..."/>
              <button type="button" className="btn btn-primary ml-2">Search</button>
            </form>
            <div>
              <a href="#">Export xls</a>
            </div>
          </div>
        </nav>
        <section className="table-header py-4 mb-4">
          <div className="container">
            <div className="row justify-content-lg-around">
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>#</p>
              </div>
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>First name</p>
              </div>
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>Middle name</p>
              </div>
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>Last name</p>
              </div>
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>Date of birth</p>
              </div>
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>City</p>
              </div>
              <div className="col-lg-1 mb-1 mb-lg-0">
                <p>School</p>
              </div>
            </div>
          </div>
        </section>

        <section className="table-body">
          <div className="container">
            <ul className="list-unstyled">
              <li className="py-4">
                <div className="row justify-content-lg-around">
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>1</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Daniyar</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Temirbekovich</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Zhadyrassyn</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>11/06/1997</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Qyzylorda</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>3</p>
                  </div>
                </div>
              </li>
              <li className="py-4">
                <div className="row justify-content-lg-around">
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>2</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Daniyar</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Temirbekovich</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Zhadyrassyn</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>11/06/1997</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>Qyzylorda</p>
                  </div>
                  <div className="col-lg-1 mb-1 mb-lg-0">
                    <p>3</p>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </section>
      </div>

    )
  }
}

export default ModeratorApp