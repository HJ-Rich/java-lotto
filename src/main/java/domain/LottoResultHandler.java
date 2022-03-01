package domain;

import static domain.Lotto.LOTTO_PRICE;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import dto.LottoResultDto;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public class LottoResultHandler {
    private LottoResultHandler() {
    }

    public static LottoResultDto getLottoResultDto(WinningLotto winningLotto, Lottos lottos) {
        Map<Rank, Long> lottoResult = getLottoResultGroupByRank(winningLotto, lottos);
        long totalReturn = getTotalReturnByLottoResult(lottoResult);
        double rateOfReturn = getRateOfReturn(lottos, totalReturn);

        return LottoResultDto.of(lottoResult, rateOfReturn);
    }

    private static Map<Rank, Long> getLottoResultGroupByRank(WinningLotto winningLotto, Lottos lottos) {
        return lottos.getLottos()
                .stream()
                .map(winningLotto::getRankByLotto)
                .collect(groupingBy(Function.identity(), () -> new EnumMap<>(Rank.class), counting()));
    }

    private static long getTotalReturnByLottoResult(Map<Rank, Long> lottoResult) {
        return lottoResult.entrySet()
                .stream()
                .mapToLong(LottoResultHandler::getPrizeByRankAndTimes)
                .sum();
    }

    private static long getPrizeByRankAndTimes(Entry<Rank, Long> map) {
        return map.getKey().getPrize() * map.getValue();
    }

    private static double getRateOfReturn(Lottos lottos, long totalReturn) {
        return (double) totalReturn / (lottos.getLottos().size() * LOTTO_PRICE);
    }
}
