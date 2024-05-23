export interface Match {
    id: number;
    id_player1: number | null;
    id_player2: number | null;
    id_player3: number | null;
    id_player4: number | null;
    scoreSet1A: number | null;
    scoreSet1B: number | null;
    scoreSet2A: number | null;
    scoreSet2B: number | null;
    scoreSet3A: number | null;
    scoreSet3B: number | null;
    matchResult: string | null;
    date: string | null;
    open: boolean;
    type: string | null;
    level: number | null;
    confirmResult1: boolean;
    confirmResult2: boolean;
    chat: number | null;
    court: number | null;
  }
