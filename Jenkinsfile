
pipeline {
    agent {
      docker {
        image 'maven:3-alpine'
        args '-v /root/.m2:/root/.m2'
      }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/deliver.sh'
            }
        }
        stage('Deliver for development') {
          when {
            branch 'develop'
          }
          steps {
            sh 'mvn -B -DskipTests clean package'
          }
        }
        stage('Deploy for production') {
          when {
            branch 'master'
          }
          steps {
            sh 'mvn -B -DskipTests clean install'
          }
        }
    }
}