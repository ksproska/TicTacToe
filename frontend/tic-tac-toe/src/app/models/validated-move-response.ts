export interface ValidatedMoveResponse {
  index: number
  sign: string
  nextPlayer: number
  isGameFinished: boolean
  winningIndexes: number[]
}
