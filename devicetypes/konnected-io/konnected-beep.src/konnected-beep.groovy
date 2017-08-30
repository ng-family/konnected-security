/**
 *  Konnected Beep
 *
 *  Copyright 2017 konnected.io
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
  definition (name: "Konnected Beep", namespace: "konnected-io", author: "konnected.io") {
    capability "Switch"
    capability "Actuator"
    capability "Momentary"
    capability "Tone"
  }

  preferences {
    input name: "beepDuration", type: "number", title: "Beep Duration",
          description: "Duration of each beep tone (in milliseconds)"
    input name: "beepPause", type: "number", title: "Beep Pause",
          description: "Duration of each pause between beep tones (in milliseconds)"
    input name: "beepRepeat", type: "number", title: "Beep Repeat",
          description: "Number of times to repeat the tone"
  }

  tiles {
    multiAttributeTile(name:"main", type: "generic", width: 6, height: 4, canChangeIcon: true) {
      tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
        attributeState "off", label: 'Beep', action: "tone.beep", backgroundColor: "#ffffff", nextState: "pushed"
        attributeState "on", label: 'Beep', action: "tone.beep", backgroundColor: "#00a0dc"
        attributeState "pushed", label:'pushed', action: "tone.beep", backgroundColor:"#00a0dc", nextState: "off"
      }
    }
    main "main"
    details "main"
  }
}

def updatePinState(Integer state) {
  sendEvent(name: "switch", value: "on", isStateChange: true, display: false)
  sendEvent(name: "switch", value: "off", isStateChange: true, display: false)
}

def off() {
  beep()
}

def on() {
  beep()
}

def push() {
  beep()
}

def beep() {
  parent.deviceUpdateDeviceState(device.deviceNetworkId, 1, [
    momentary : beepDuration ?: 500,
    times     : beepRepeat ?: 3,
    pause     : beepPause ?: 500
  ])
}
